#include <OneWire.h>
 
#include <EtherCard.h>

#include <SPI.h>
#include <Mirf.h>
#include <nRF24L01.h>
#include <MirfHardwareSpiDriver.h>

#include <dht.h>  

dht DHT;  

#define DHT11_PIN 4 
 
#define OUT
 
#define TIME1  3000
 
#define TIME2 2000
 
static String switchStatus; 

int data;
int words;
 
static byte mymac[] = { 
 
  0x74,0x69,0x69,0x2D,0x30,0x31 };//MAC地址只要与网络上设备不冲突就行
 
 
char website[] PROGMEM = "loveniwed.com";
 
char urlBuf2[] PROGMEM = "/PacJiaJu/Pac/";//填写自己的传感器和设备信息

byte Ethernet::buffer[700];  
static long timer;
 int WenDu__=29; 
static void my_result_cb1 (byte status, word off, word len) {
 
  String reply=(const char*)Ethernet::buffer + off +58;
  Serial.println(reply); 
 
  switchStatus = reply.substring(0,1);
 
  Serial.print("<<< reply ");
 
  Serial.print(millis() - timer);
 
  Serial.println(" ms");
 
  Serial.print("Switch Status:");
 
  //Serial.println(switchStatus);
  
  WenDu__ =(int)DHT.temperature;
  
  //int status2 =switchStatus.toInt();
   // Mirf.send((byte *)&status2);  
   Serial.println("");
  Mirf.send((byte *)&WenDu__);
     while(Mirf.isSending())
     { }
  
   Serial.println((const char*) Ethernet::buffer + off );
 
  Serial.println();
 
}
void setup () {

  Serial.begin(9600);
  Mirf.spi = &MirfHardwareSpi;
  Mirf.init();
  Mirf.setRADDR((byte *)"jingbao");
  Mirf.payload = sizeof(int);
  Mirf.config();
  //pinMode(6,OUTPUT);

   
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
  ether.packetLoop(ether.packetReceive());
  String reply;
 
  if (millis() > timer + TIME1) {
 
    timer = millis();
 
    Serial.println("\n>>> REQ2");
 
    static char buf[20];
 
    
 Serial.println("----------");
    if (!ether.dnsLookup(website))
 
      Serial.println("DNS failed");
 
    ether.printIp("Server: ", ether.hisip);
     if(Mirf.dataReady()) {
     Mirf.getData((byte *)&data);
     words=data;
     Mirf.rxFifoEmpty();
       Serial.print(words+"jieshoudaodeshuju");
       
       char str10[10];
        sprintf(str10,"%d",words);
          char   str1[50]="updateKongTiao?deviceid=6&status=";
          strcat(str1,str10); 
     ether.browseUrl(urlBuf2,str1, website, my_result_cb1);
     
    
  }

 }   

}







