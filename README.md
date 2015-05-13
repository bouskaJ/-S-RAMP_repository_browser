S-RAMP repository browser
==========================
##Summary
S-RAMP repository browser provides view for using S-RAMP repository in Eclipse Based applications, such as Eclipse, JBoss Developer Studio etc.

##Building S-RAMP repository browser
We use Maven 3.x to build this software. The following commands compile the code, install the JAR into your local Maven repository and run all  the tests:

      1. $ git clone https://github.com/bouskaJ/-S-RAMP_repository_browser.git
      2. $ cd ./-S-RAMP_repository_browser/final
      3. $ mvn clean install

Note: To run all the tests successfully you need running S-RAMP repository on your localhost. Your login must be “admin” and password “admin123” or you can change this property in ./-S-RAMP_repository_browser/final/srampRepositoryBrowser.test/config.properties file.

If you want to build software without testing you can run this command instead of step 3.

    $ mvn clean package

##Installing 
After building successfully, you can install plug-in to your development environment. Copy ./srampRepositoryBrowser/target/srampRepositoryBrowser-xxx.jar to the dropins folder of your IDE and then restart IDE.

##Basic Usage
To connect to the S-RAMP repository click to the Connect to server button. If you need to import some files to repository, use the Import to S-RAMP repository button in the context menu of your Package Explorer. To delete artifacts or importing artifacts to your project's workspace, use buttons in the context menu in Browser View.



