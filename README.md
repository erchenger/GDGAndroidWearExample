Android Wear sample app. The app:

1. Schdules an alarm to run every 15 mins
2. Makes a network request to get a json object from Dropbox
3. Makes an Android Wear and Android notfication with two actions
4. Process the actions and dismisses the notifications 


To run the sample:

1. Clone the repo

2. Create an Android Wear emulator using AVD

3. Download the Android Wear Beta app from Google Play (requires approval to beta group)

4. Connect the Android Wear emulator to your usb device running the Android Wear Beta App with: ./adb -d forward tcp:5601 tcp:5601

5. Run the sample 
