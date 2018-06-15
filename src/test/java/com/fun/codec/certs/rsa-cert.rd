pwd:alibaba
openssl genrsa -out private-rsa.key 1024
openssl req -new -x509 -key private-rsa.key -out public-rsa-10years.cer -days 3650
openssl pkcs12 -export -out private-rsa.pfx -inkey private-rsa.key -in public-rsa-10years.cer