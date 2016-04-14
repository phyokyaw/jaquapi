#!/usr/bin/env python2.7  

import RPi.GPIO as GPIO  
import paho.mqtt.client as mqtt
import spidev
import time
from os import listdir
from os.path import isfile
import threading

GPIO.setmode(GPIO.BCM)

device_name = "/fishtank"
gpio_switches = [5, 6, 12, 13, 16]
ph_analog_channel = 0
ph_read_count = 40
ph_read_delay_in_sec = 0.5
report_inteval_in_sec = 60
user_name = 'admin'
password = 'admin'
mqtt_server_ip = "localhost"
mqtt_server_port = 1883

for key in gpio_switches:
    GPIO.setup(key, GPIO.IN)

# Open SPI bus
spi = spidev.SpiDev()
spi.open(0,0)

connected = False

def get_ph():
    adc = spi.xfer2([1,(8+ph_analog_channel)<<4,0])
    return ((adc[1]&3) << 8) + adc[2]

def publish_ph():
    phdata = 0.0 
    for x in range(0, ph_read_count):
        phdata += get_ph()
        time.sleep(ph_read_delay_in_sec)
    phdata = 3.5 * ((phdata * 5.0) / float(1024))
    phdata = round(phdata / ph_read_count, 2)
    (result, mid) = client.publish(device_name + "/ph", phdata)
    print "ph " + str(phdata)

def publish_temperature():
    for f in listdir("/sys/bus/w1/devices/"):
    	filename = "/sys/bus/w1/devices/" + f + "/w1_slave"
        if isfile(filename):
            text = ""
            with open(filename, 'r') as tfile:
                text = tfile.read()
            temperaturedata = text.split("\n")[1].split(" ")[9]
            temperature = float(temperaturedata[2:])
            temperature = temperature / 1000
            (result, mid) = client.publish(device_name + "/temperature/" + f, temperature)
            print "temp " + str(temperature)

client = mqtt.Client()

def switch_changed(channel):
    (result, mid) = client.publish(device_name + "/switch/%d" % gpio_switches.index(channel), GPIO.input(channel))

def on_connect(client, userdata, flags, rc):
    global connected
    if rc == 0:
        print "connected"
        connected = True
        client.will_set(device_name + "/connection", "0", 0, retain=True)
        client.publish(device_name + "/connection", "1", 0, retain=True)
        for key in gpio_switches:
            switch_changed(key)
        start_publish_sensors_job()

def on_disconnect(client, userdata, rc):
    global connected
    connected = False

def publish_sensors():
    publish_ph()
    publish_temperature()

def start_publish_sensors_job():
    t = threading.Thread(target=publish_sensors)
    t.start()

for key in gpio_switches:
    GPIO.add_event_detect(key, GPIO.FALLING, callback=switch_changed, bouncetime=50)


client.on_connect = on_connect
client.on_disconnect = on_disconnect

client.username_pw_set(user_name, password)
client.connect(mqtt_server_ip, mqtt_server_port, 60)
client.loop_start()

try:
    while True:
        if connected == True:
            start_publish_sensors_job()
        time.sleep(report_inteval_in_sec)
except KeyboardInterrupt:  
    # here you put any code you want to run before the program   
    # exits when you press CTRL+C
    client.disconnect()
    print "disconnected"
finally:
    GPIO.cleanup()           # clean up GPIO on normal exit
    print "cleanup"
