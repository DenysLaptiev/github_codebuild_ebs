keystore is a database of "cryptographic keys, X.509 certificate chains, and trusted certificates".

most common formats used for keystores:
1)JKS - a proprietary format specific for Java,
2)PKCS12 - an industry-standard format (recommended since Java 9)

1. Generate self-signed certificate
   keytool -genkeypair -alias springboot1 -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore springboot1.p12 -validity 3650 -storepass password1
   keytool -genkeypair -alias springboot2 -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore springboot2.p12 -validity 3650 -storepass password2

2. Verify the keystore content:
   keytool -list -v -keystore springboot1.p12
   Enter keystore password:password2

   keytool -list -v -keystore springboot2.p12
   Enter keystore password:password2

======================  Create and sign an X509 certificate  ======================
create an X509 certificate for your application with OpenSSL.
https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/configuring-https-ssl.html
1. OpenSSL is a standard, open source library that supports a wide range of cryptographic functions, including the creation and signing of x509 certificates.
openssl version

2. create an RSA private key to create your certificate signing request (CSR)
openssl genrsa 2048 > ms1_privatekey.pem
openssl genrsa 2048 > ms2_privatekey.pem

3. create a CSR - a file you send to a certificate authority (CA) to apply for a digital server certificate.
openssl req -new -key ms1_privatekey.pem -out ms1_csr.pem
openssl req -new -key ms2_privatekey.pem -out ms2_csr.pem
Country Name=US
State or Province=Washington
Locality Name=Seattle
Organization Name=Example Corporation
Organizational Unit=Marketing
Common Name=www.example.com
Email address=someone@example.com

A challenge password=
An optional company name=


4. To sign the certificate, use the openssl x509 command. 
The following example uses the private key from the previous step (privatekey.pem) and the signing request (csr.pem) to create a public certificate named public.crt that is valid for 365 days.
openssl x509 -req -days 365 -in csr.pem -signkey privatekey.pem -out public.crt
openssl x509 -req -days 365 -in ms1_csr.pem -signkey ms1_privatekey.pem -out ms1_public.crt
openssl x509 -req -days 365 -in ms2_csr.pem -signkey ms2_privatekey.pem -out ms2_public.crt


5. Upload a certificate to IAM
======================  Upload a certificate to IAM  ======================
https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/configuring-https-ssl-upload.html

The following command uploads a self-signed certificate named https-cert.crt with a private key named private-key.pem:
$ aws iam upload-server-certificate --server-certificate-name elastic-beanstalk-x509 --certificate-body file://https-cert.crt --private-key file://private-key.pem
{
"ServerCertificateMetadata": {
"ServerCertificateId": "AS5YBEIONO2Q7CAIHKNGC",
"ServerCertificateName": "elastic-beanstalk-x509",
"Expiration": "2017-01-31T23:06:22Z",
"Path": "/",
"Arn": "arn:aws:iam::123456789012:server-certificate/elastic-beanstalk-x509",
"UploadDate": "2016-02-01T23:10:34.167Z"
}
}


$ aws iam upload-server-certificate --server-certificate-name ms1-elastic-beanstalk-x509 --certificate-body file://ms1_public.crt --private-key file://ms1_privatekey.pem
$ aws iam upload-server-certificate --server-certificate-name ms2-elastic-beanstalk-x509 --certificate-body file://ms2_public.crt --private-key file://ms2_privatekey.pem

6. Configure Load Balancer for Elastic Beanstalk Environment
Add Listener
Listener Port=443 Listener Protocol=HTTPS	SSL certificate=arn:aws:iam::026806867949:server-certificate/ms2-elastic-beanstalk-x509	

//Configure updates, monitoring, and logging 
//Health monitoring rule customization
//Ignore application 4xx=Activated
//Ignore load balancer 4xx=Activated


7. Create new GitHub repository github_codebuild_ebs

8. In cmd
git init
git status
git add ms_1
git add ms_2
git add README.md
git add pom.xml
git add .gitignore
git add github_codebuild_ebs.png
git add github_codebuild_ebs.png
git status
git commit -m "first commit"
git remote add origin https://github.com/DenysLaptiev/github_codebuild_ebs.git
git push -u origin main


9. Add a collaborator (DenisLaptev) to github_codebuild_ebs in remote GitHub repository (of DenysLaptiev)
10. Add buildspec.yml