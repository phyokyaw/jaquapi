#!/usr/bin/env python2.7  

import RPi.GPIO as GPIO  
import paho.mqtt.client as mqtt
import spidev

GPIO.setmode(GPIO.BCM) 
GPIO.setup(16, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

# Open SPI bus
spi = spidev.SpiDev()
spi.open(0,0)

def ReadChannel(channel):
  adc = spi.xfer2([1,(8+channel)<<4,0])
  data = ((adc[1]&3) << 8) + adc[2]
  return data

client = mqtt.Client()

def my_callback(channel):
    (result, mid) = client.publish("switch %d" % channel, GPIO.input(channel))
    (result, mid) = client.publish("switch ph", ReadChannel(0))

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.will_set("clients/monitor", "0", 0, retain=True)
    client.publish("clients/monitor", "1", 0, retain=True)
    client.subscribe("switch %d" % 16, 0)
    client.subscribe("switch ph", 0)
    

def on_message(client, userdata, msg):
	print(msg.topic+" "+str(msg.payload))

GPIO.add_event_detect(16, GPIO.FALLING, callback=my_callback, bouncetime=300)

#try:  
#    print "Waiting for rising edge on port 24"  
#    GPIO.wait_for_edge(24, GPIO.RISING)  
#    print "Rising edge detected on port 24. Here endeth the third lesson."  
#
#except KeyboardInterrupt:
#    GPIO.cleanup()       # clean up GPIO on CTRL+C exit


client.on_connect = on_connect
client.on_message = on_message

client.subscribe("switch", 0)

client.username_pw_set('admin','password')
client.connect("192.168.0.11", 61613, 60)

client.loop_forever()

GPIO.cleanup()           # clean up GPIO on normal exit  
