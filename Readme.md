<h2>Description</h2>
RTS (Realtime scrapper) is a tool developed to scrap all pasties,github,reddit..etc in real time to identify occurrence of search terms configured. Upon match an email will be triggered. Thus allowing company to react in case of leakage of code, any hacks tweeted..etc.. and harden themselves against an attack before it goes viral.

The same tool in malicious user hands can be used offensively to get update on any latest hacks, code leakage etc..

List of sites which wil be monitored are:
<ul>
<li>twitter</li>
<li>pastebin.com</li>
<li>reddit</li>
<li>github</li>
<li>codepad.org</li>
<li>lpaste.net</li>
<li>dumpz.org</li>
<li>snipplr.com</li>
<li>paste.org.ru</li>
<li>gist.github.com</li>
<li>pastebin.ca</li>
<li>kpaste.net</li>
<li>slexy.org</li>
<li>ideone.com</li>
<li>pastebin.fr</li>
</ul>
For architecture information and details of how this tool work refer documnetation folder in this repository.

<h3>Configuration</h3>

Before using this tool is is neccessary to understand the properties file present in scrapper_config directory.  
<ul>
<li>consumer.properties: Holds all the neccessary config data needed for consumer (Refer apache Kafka guide for more information). The values present here are default options and does nto require any changes</li>  
<li>producer.properties: Holds all the neccessary config data needed for Producer (Refer apache Kafka guide for more information).The values present here are default options and does nto require any changes</li>  
<li>email.properties: Configure SMTP server with email id's.</li>  
<li>scanner-configuration.properties: This is the core configuration file. Update all the data of twitter for enabling search on twitter/github(To get tokens and key refer respective sites). For  pastie sites and reddit there is no need for any changes in config.</li>  
However in all cases make sure to change "searchterms" to values of our choice to search. If there are multiple search terms then add them seperate by comma as shown with example terms in config file.
</ul>


<h3>How to use the tool</h3> 
<ul>
<li>Install JDK</li> 
<li>Install mvn and set the path</li> 
<li>Start the zookeeper and Kafka Server (Refer https://kafka.apache.org/documentation/#quickstart for more information) </li>    
<li>Create a kafka topic </li> 
<li>Navigate to main folder. Run command "mvn clean install -DskipTests". This willbuild the code.</li> 
<li>Navigate to scraptool/tartget  </li> 
<li>Run the command "java -jar scraptool-1.0-SNAPSHOT-standalone.jar -t "Kafka Topic name" -c "complete path of config directory"" </li>   
</ul>

<h3>Contributors</h3>
<ul>
<li>Naveen Rudrappa </li>                                                                                                              
<li>Padma Shivakumar</li> 
</ul>
