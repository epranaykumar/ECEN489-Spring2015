# Secure Sockets Layer (SSL)

[Transport Layer Security](http://en.wikipedia.org/wiki/Transport_Layer_Security) (TLS) and its predecessor, [Secure Sockets Layer](http://en.wikipedia.org/wiki/Transport_Layer_Security) (SSL), are cryptographic protocols designed to provide secure data transfers over communication networks.
These protocols rely on certificates and hence asymmetric cryptography to authenticate the communicating entities.
A session key is subsequently employed to encrypt data flowing between the parties.
To implement these protocols, certificate authorities and a public key infrastructure are necessary to verify the relation between a certificate and its owner, as well as to generate, sign, and administer the validity of certificates.


## Texas A&M Certificate Service

[Texas A&M University](https://cert.tamu.edu/) has subscribed to the [InCommon Certificate Service](https://www.incommon.org/certificates/).
This service provides unlimited SSL certificates to domains owned by the university.


## Certificate Issuance Process

* Generate a Certificate Service Request (CSR)
```
> openssl req -newkey rsa:2048 -keyout {$hostname}.key -out {$hostname}.csr
Country Name: US
State or Province (full name) Name: Texas
Locality Name (city): College Station
Organization Name: Texas A&M University
Organizational Unit Name: {$department}
Common Name: {$hostname}.tamu.edu
Email Address []:
A challenge password []:
An optional company name []:
```
* Submit Request to [Certificate Service](https://cert.tamu.edu/request/)
* Obtain Approvals: InCommon will send email with link
* Get Certificate
  * Download site certificate ```X509 Base64 Certificate only``` as ```{$hostname}.cer```
  * Download intermediate chain certificate ```X509 Base64 intermediates only``` as ```{$hostname}-incommon.cer```

## Verify Certificate

Before proceeding, it may be useful to check key, certificate and verirify certificate chains.
```
openssl rsa -in {$hostname}.key -check
openssl x509 -in {$hostname}.cer -text -noout
openssl verify -verbose -CAfile {$hostname}-incommon.cer {$hostname}.cer
```
It may also be useful to make sure that MD5 hashes match. 
```
openssl rsa -noout -modulus -in {$hostname}.key | openssl md5
openssl x509 -noout -modulus -in {$hostname}.cer | openssl md5
```

## Apache HTTPD Configuration

Place key, site certificate, and intermediate chain certificate in secure location on server with proper owner and permissions.
After placing key, site certificate, and intermediate chain certificate on server; edit appropriate apache SSL configuration (```/etc/apache2```).
```
SSLEngine on
SSLCertificateFile /path/to/{$hostname}.cer
SSLCertificateKeyFile /path/to/{$hostname}.key
SSLCertificateChainFile /path/to/{$hostname}-incommon.cer
```

## Generating SSL Certificate
First step: generate CSR and submitted it to CA, at the same time, a private key will also be generated.
[CSR generation instruction given by TAMU](https://cert.tamu.edu/docs/csr/)
This process is not standard for tomcat7. This is because tomcat7 use keystore. Keytool does not provide such basic functionality like importing private key to keystore
[CSR generation for tomcat7](https://support.comodo.com/index.php?/Default/Knowledgebase/Article/View/90/19/csr-generation-java-based-web-servers-tomcat-using-keytool) provide instruction on tomcat. The CSR file will be generated with the keystore file.
However, generating the private key and CSR privately can also work. The workaround is in the following link
[importing an existing x509 certificate and private key in Java keystore to use in ssl](http://stackoverflow.com/questions/906402/importing-an-existing-x509-certificate-and-private-key-in-java-keystore-to-use-i). By converting x509 Cert and Key to a pkcs12 file and then converting the pkcs12 file to a java keystore.

Second step: After submitting the CSR, CA will approve and sent out email with link or a direct zip file which the user can download. After downloading the certificates describe above, the certificates will need to be added to the keystore file by following this [process](https://support.comodo.com/index.php?/Default/Knowledgebase/Article/View/638/0/certificate-installation-java-based-web-servers-tomcat-using-keytool).

Third step: After adding the keystore file is finalize, it will be used by tomcat. If using apache2, the certificates can be used directly.