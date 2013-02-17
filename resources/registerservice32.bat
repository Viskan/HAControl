
echo "Installing HAControl Service, 32bit"
"%~dp0\HAControl32.exe" //IS --Classpath "%~dp0\hacontrol.jar" --StartMode=jvm --StartClass=com.viskan.hacontrol.Main --StartMethod=start --StopMode=jvm --StopClass=com.viskan.hacontrol.Main --StopMethod=stop --Startup=auto
pause


