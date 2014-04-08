#!/usr/bin/python

import spidev
import os
import time
import sys

delay = 0.1
ph_channel = 0
offset = 0.0

if len(sys.argv) == 2:
        offset = float(sys.argv[1])

# Open SPI bus
spi = spidev.SpiDev()
spi.open(0,0)

# Function to read SPI data from MCP3008 chip
# Channel must be an integer 0-7
def ReadChannel(channel):
  adc = spi.xfer2([1,(8+channel)<<4,0])
  data = ((adc[1]&3) << 8) + adc[2]
  return data

readings = []

for x in range(0, 9):
  # Read the ph sensor data
  ph_level = ReadChannel(ph_channel)
  readings.append(ph_level)
  time.sleep(delay)

readings.sort()
avg = 0

for x in range(2, 7):
  avg += readings[x]

phValue = avg * 5.0 / 1024 / 6
phValue = 3.5 * phValue + offset
print(phValue)