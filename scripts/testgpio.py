#!/usr/bin/env python2.7  

import RPi.GPIO as GPIO  
import paho.mqtt.client as mqtt
import spidev
import time

GPIO.setmode(GPIO.BCM)

gpio_switches = {'1', '2', '3', '4', '5'}
temperature_files = {'est', 'test', 'test'}
ph_analog_channel = 0
report_inteval_in_sec = 60

for key in gpio_switches
	GPIO.setup(int(key), GPIO.IN)

for key in gpio_switches
	GPIO.add_event_detect(int(key), GPIO.FALLING, callback=switch_changed, bouncetime=300)

# Open SPI bus
spi = spidev.SpiDev()
spi.open(0,0)

def ph():
	adc = spi.xfer2([1,(8+ph_analog_channel)<<4,0])
	data = ((adc[1]&3) << 8) + adc[2]
	phdata = 3.5 * ((data * 5.0) / float(1023))
	phdata = round(phdata, 2)
	return phdata

def temperature():
	tfile = open("/sys/bus/w1/devices/10-000802824e58/w1_slave") 
	text = tfile.read()
	tfile.close()
	temperaturedata = text.split("\n")[1].split(" ")[9]
	temperature = float(temperaturedata[2:])
	return temperature / 1000

client = mqtt.Client()

def switch_changed(channel):
    (result, mid) = client.publish("switch %d" % channel, GPIO.input(channel))
    

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.will_set("clients/monitor", "0", 0, retain=True)
    client.publish("clients/monitor", "1", 0, retain=True)
#    client.subscribe("switch %d" % 16, 0)
#    client.subscribe("switch ph", 0)


#def on_message(client, userdata, msg):
#	print(msg.topic+" "+str(msg.payload))


#try:  
#    print "Waiting for rising edge on port 24"  
#    GPIO.wait_for_edge(24, GPIO.RISING)  
#    print "Rising edge detected on port 24. Here endeth the third lesson."  
#
#except KeyboardInterrupt:
#    GPIO.cleanup()       # clean up GPIO on CTRL+C exit


client.on_connect = on_connect
# client.on_message = on_message

# client.subscribe("switch", 0)

client.username_pw_set('admin','password')
client.connect("192.168.0.11", 61613, 60)
client.loop_start()

while True:
	time.sleep(report_inteval_in_sec)
    (result, mid) = client.publish("clients/ph", ph())
	(result, mid) = client.publish("clients/temp", temperature())

GPIO.cleanup()           # clean up GPIO on normal exit  
