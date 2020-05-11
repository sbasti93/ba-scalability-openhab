@echo off

FOR /L %%d IN (1 1 %1) DO  (

	start /Min publisher.bat %%d

)
