#include <OneWire.h>
 
#include <EtherCard.h>

#include <SPI.h>
#include <Mirf.h>
#include <nRF24L01.h>
#include <MirfHardwareSpiDriver.h>
 
#define OUT
 
#define TIME1  15000
 
#define TIME2 2000
 
 
 
static byte mymac[] = { 
 
  0x74,0x69,0x69,0x2D,0x30,0x31 };//MAC地址只要与网络上设备不冲突就行
 
 
char website[] PROGMEM = "loveniwed.com";
 
char urlBuf2[] PROGMEM = "/PacJiaJu/Pac/";//填写自己的传感器和设备信息
 
char urlBuf1[] PROGMEM = "/v1.0/device/12495/sensor/20320/";
 
char apiKey[] PROGMEM = "U-ApiKey:6c6d9f735 da71052f2967a8efe4ad1b9";//填写自己的API key
 
byte Ethernet::buffer[700];  
static long timer;
 
 
 
static void my_result_cb2 (byte status, word off, word len) {
 
  Serial.print("<<< reply2 ");
 
  Serial.print(millis() - timer);
 
  Serial.println(" mss");
 
  Serial.println((const char*) Ethernet::buffer + off );
 
}
String switchStatus;
 
 
static void my_result_cb1 (byte status, word off, word len) {
 
  String reply=(const char*)Ethernet::buffer + off +56;
  Serial.println(reply); 
 
  switchStatus = reply.substring(0,1);
 
  Serial.print("<<< reply ");
 
  Serial.print(millis() - timer);
 
  Serial.println(" ms");
 
  Serial.print("Switch Status:");
 
  Serial.println(switchStatus);
  
   Serial.println((const char*) Ethernet::buffer + off );
 
  Serial.println();
 
}
void setup () {

  Serial.begin(9600);
  Mirf.spi = &MirfHardwareSpi;  
  Mirf.init();
   Mirf.setTADDR((byte *)"deng");
  Mirf.payload = sizeof(char);           
   Mirf.config();   
  if (!ether.begin(sizeof Ethernet::buffer, mymac)) //自动获取IP地址
    Serial.println( "Failed to access Ethernet controller");
  else
    Serial.println("Ethernet controller initialized");
  Serial.println();
 
  if (!ether.dhcpSetup())
    Serial.println("Failed to get configuration from DHCP");
  else
    Serial.println("DHCP configu    ration done");
 
  ether.printIp("IP Address:\t", ether.myip);
  ether.printIp("Netmask:\t", ether.netmask);
  ether.printIp("Gateway:\t", ether.gwip);
  Serial.println();
 
  if (!ether.dnsLookup(website))
    Serial.println("DNS failed");
  else 
    Serial.println("DNS resolution done");  
  ether.printIp("SRV IP:\t", ether.hisip);
  Serial.println();
 
 

  timer = 0;
 
}
void loop () {
  digitalWrite(4,LOW);
  ether.packetLoop(ether.packetReceive());
  String reply;
  /*if (millis() > timer + TIME1) {
 
    timer = millis();
 
    if (!ether.dnsLookup(website))
 
      Serial.println("DNS failed");
 
    ether.printIp("Server: ", ether.hisip);
 
    Serial.println("\n>>> REQ");
 
    ether.browseUrl(urlBuf1, "datapoints", website,apiKey, my_result_cb1);
  }
 
    if(switchStatus=="0")
    {
      digitalWrite(4,LOW);
    }
    if(switchStatus=="1")
    { 
      digitalWrite(4,HIGH);
    }*/
  ether.packetLoop(ether.packetReceive());
 
 
  if (millis() > timer + TIME1) {
 
    timer = millis();
 
    Serial.println("\n>>> REQ2");
 
    static char buf[20];
 
    get_send_string(buf);
 Serial.println("----------");
    if (!ether.dnsLookup(website))
 
      Serial.println("DNS failed");
 
    ether.printIp("Server: ", ether.hisip);
 
    //ether.httpPost (urlBuf2, website, apiKey, buf, my_result_cb2);
     ether.browseUrl(urlBuf2, "Test", website, my_result_cb1);
     
      
      Mirf.send((byte *)&switchStatus);  
 
     while(Mirf.isSending())
     { }
 }   
  }
}
 
 
 
void get_send_string(OUT char *p){ //处理LM35的数据
  int n=analogRead(A0);
  
  int va = (n * (5.0 / 1023.0 * 100)) * 100;
  //int data=(125*val)>>8;
  uint16_t Tc_100 = va;
  Serial.println(va);
  uint8_t whole, fract;
 
  whole = Tc_100 / 100;
  fract = Tc_100 % 100;
   
  sprintf(p,"{\"value\":%d.%d}",whole,fract);
  
}



