@echo off
cd C:\Program Files\mosquitto
:start_publish
	mosquitto_pub -h 192.168.178.83 -t testchannel/test%%d -m %1 -q 2
	timeout /T 30
	goto :start_publish