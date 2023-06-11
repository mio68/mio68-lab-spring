### Create server certificates
see https://docs.oracle.com/javase/8/docs/technotes/tools/windows/keytool.html#keytool_option_genkeypair Generate Certificates for an SSL Server

* move to mio68-lab-sping-web-mtls directory ```cd mio68-lab-spring-web-mtls```
* Use default password ```changeit``` for all key stores.

```
# Generate root key pair. Use name Test Root.
keytool -genkeypair -keyalg RSA -keysize 2048 -keystore certs/ca/root.jks -alias root -ext bc:c
 
# Generate CA key pair.  Use name Test CA
keytool -genkeypair -keyalg RSA -keysize 2048 -keystore certs/ca/ca.jks -alias ca -ext bc:c

# Generate key pair for test server. Use name Test Server. SAN includes locahost for local tests.
keytool -genkeypair -keyalg RSA -keysize 2048 -storepass changeit -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore certs/server/server.jks -alias server

# Export root certificate 
keytool -keystore certs/ca/root.jks -alias root -exportcert -rfc > certs/ca/root.pem

# Generate certificate request for CA, and then generate certificate for CA signed by Root 
keytool -storepass changeit -keystore certs/ca/ca.jks -certreq -alias ca | keytool -storepass changeit -keystore certs/ca/root.jks -gencert -alias root -ext BC=0 -rfc > certs/ca/ca.pem

# Create certificate chain for CA 
copy /b certs\ca\root.pem+certs\ca\ca.pem certs\ca\cachain.pem

# Import CA chain to CA key store. Trust Root certificate with yes answer.
keytool -keystore certs/ca/ca.jks -importcert -alias ca -file certs/ca/cachain.pem

# Generate certificate request for Server, and then generate certificate for Server signed by CA 
keytool -storepass changeit -keystore certs/server/server.jks -certreq -alias server | keytool -storepass changeit -keystore certs/ca/ca.jks -gencert -alias ca -ext "ku:c=dig,keyEncipherment" -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -rfc > certs/server/server.pem

# Create certificate chain for Server 
copy /b certs\ca\root.pem+certs\ca\ca.pem+certs\server\server.pem certs\server\serverchain.pem

# Import Server chain to Server key store
keytool -keystore certs/server/server.jks -importcert -alias server -file certs/server/serverchain.pem
```

### Create trust store for clint and server
Add only Test CA certificate to trust store.

```
keytool -storepass changeit -keystore certs/client/truststore.jks -alias "test ca" -importcert  -file certs/ca/ca.pem
copy /b certs\client\truststore.jks certs\server\truststore.jks
```

### Create client certificate for mTLS

```
# Create client certificate with name Test Client
keytool -genkeypair -keyalg RSA -keysize 2048 -storepass changeit -keystore certs/client/client.jks -alias client

# Generate certificate request for Client, and then generate certificate for Client signed by CA 
keytool -storepass changeit -keystore certs/client/client.jks -certreq -alias client | keytool -storepass changeit -keystore certs/ca/ca.jks -gencert -alias ca -ext "ku:c=dig,keyEncipherment" -rfc > certs/client/client.pem

# Create certificate chain for Client 
copy /b certs\ca\root.pem+certs\ca\ca.pem+certs\client\client.pem certs\client\clientchain.pem

# Import Client chain to Client key store
keytool -keystore certs/client/client.jks -importcert -alias client -file certs/client/clientchain.pem
```