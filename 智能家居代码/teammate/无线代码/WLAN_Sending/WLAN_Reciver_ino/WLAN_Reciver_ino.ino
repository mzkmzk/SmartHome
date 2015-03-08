#include <Mirf.h>
#include <MirfHardwareSpiDriver.h>
#include <MirfSpiDriver.h>
#include <nRF24L01.h>
#include <SPI.h>

int data;
int words;

void setup() {
  Serial.begin(9600);
  Mirf.spi = &MirfHardwareSpi;
  Mirf.init();
  Mirf.setRADDR((byte *)"jingbao");
  Mirf.payload = sizeof(int);
  Mirf.config();
  pinMode(6,OUTPUT);
}

void loop() {
  if(Mirf.dataReady()) {
     Mirf.getData((byte *)&data);
     words=data;
     Mirf.rxFifoEmpty();
      
     Serial.println(words);
  }
}

void Alarm() //蜂鸣器发出警报
{
for(int i=0;i<100;i++){
digitalWrite(6,HIGH); //发声音
delay(2);
digitalWrite(6,LOW); //不发声音
delay(2); //修改延时时间，改变发声频率
}
}

