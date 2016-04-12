#!/usr/bin/env python2.7  

import RPi.GPIO as GPIO  
import paho.mqtt.client as mqtt
import spidev
import time
from os import listdir
from os.path import isfile

GPIO.setmode(GPIO.BCM)

device_name = "/fishtank"
gpio_switches = [5, 6, 12, 13, 16]
ph_analog_channel = 0
report_inteval_in_sec = 60
user_name = 'admin'
password = 'password'
mqtt_server_ip = "192.168.0.11"
mqtt_server_port = 61613

for key in gpio_switches:
    GPIO.setup(key, GPIO.IN)

# Open SPI bus
spi = spidev.SpiDev()
spi.open(0,0)

connected = False

def publish_ph():
    adc = spi.xfer2([1,(8+ph_analog_channel)<<4,0])
    data = ((adc[1]&3) << 8) + adc[2]
    phdata = 3.5 * ((data * 5.0) / float(1023))
    phdata = round(phdata, 2)
    (result, mid) = client.publish(device_name + "/ph", phdata)
<<<<<<< HEAD:scripts/testgpio.py
    print phdata
=======
    print "ph " + phdata
>>>>>>> e8c0ef53a1fb437215c10a978ccab211cc0b993c:scripts/sensors_mqtt_run.py

def publish_temperature():
    for f in listdir("/sys/bus/w1/devices/"):
    	filename = "/sys/bus/w1/devices/" + f + "/w1_slave"
        if isfile(filename):
            tfile = open(filename) 
            text = tfile.read()
            tfile.close()
            temperaturedata = text.split("\n")[1].split(" ")[9]
            temperature = float(temperaturedata[2:])
            temperature = temperature / 1000
            (result, mid) = client.publish(device_name + "/temperature/" + f, temperature)
            print "temp " + temperature

client = mqtt.Client()

def switch_changed(channel):
    (result, mid) = client.publish(device_name + "/switch/%d" % gpio_switches.index(channel), GPIO.input(channel))
<<<<<<< HEAD:scripts/testgpio.py
    if GPIO.input(channel) == 1:
       print "De-De"
=======
    print "channel " + channel + ": " + GPIO.input(channel)
>>>>>>> e8c0ef53a1fb437215c10a978ccab211cc0b993c:scripts/sensors_mqtt_run.py

def on_connect(client, userdata, flags, rc):
    global connected
    if rc == 0:
        connected = True
        client.will_set(device_name + "/connection", "0", 0, retain=True)
        client.publish(device_name + "/connection", "1", 0, retain=True)
<<<<<<< HEAD:scripts/testgpio.py
	publish_ph()
	publish_temperature()
=======
        for key in gpio_switches:
            switch_changed(key)
        publish_sensors()
>>>>>>> e8c0ef53a1fb437215c10a978ccab211cc0b993c:scripts/sensors_mqtt_run.py

def on_disconnect(client, userdata, rc):
    global connected
    connected = False

def publish_sensors():
    publish_ph()
    publish_temperature()

for key in gpio_switches:
<<<<<<< HEAD:scripts/testgpio.py
    GPIO.add_event_detect(key, GPIO.FALLING, callback=switch_changed, bouncetime=10)
=======
    GPIO.add_event_detect(key, GPIO.FALLING, callback=switch_changed, bouncetime=50)
>>>>>>> e8c0ef53a1fb437215c10a978ccab211cc0b993c:scripts/sensors_mqtt_run.py

client.on_connect = on_connect
client.on_disconnect = on_disconnect

client.username_pw_set(user_name,password)
client.connect(mqtt_server_ip, mqtt_server_port, 60)
client.loop_start()
<<<<<<< HEAD:scripts/testgpio.py
print "started"
while True:
    if connected == True:
        publish_ph()
        publish_temperature()
    time.sleep(report_inteval_in_sec)

GPIO.cleanup()           # clean up GPIO on normal exit  
=======

try:
	while True:
	    if connected == True:
	    	publish_sensors
	    time.sleep(report_inteval_in_sec)
except KeyboardInterrupt:  
    # here you put any code you want to run before the program   
    # exits when you press CTRL+C
    client.disconnect()
finally:
    GPIO.cleanup()           # clean up GPIO on normal exit  
>>>>>>> e8c0ef53a1fb437215c10a978ccab211cc0b993c:scripts/sensors_mqtt_run.py
