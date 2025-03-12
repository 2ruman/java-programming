#!/bin/bash

# Parameters into keytool
SERVER_KS_NAME="server_ks"
CLIENT_KS_NAME="client_ks"
SERVER_ALIAS="server_key"
CLIENT_ALIAS="server_crt"
SERVER_KS_PWD="spassword"
CLIENT_KS_PWD="cpassword"
DNAME="CN=Server, OU=Truman, O=Truman, L=Seoul, ST=Seoul, C=KR"
CDIR="certs"

# Clear all
rm ${CDIR}/${SERVER_KS_NAME}.jks ${CDIR}/${SERVER_KS_NAME}.crt ${CDIR}/${CLIENT_KS_NAME}.jks

# Generate server keys in JKS
keytool -genkeypair -keystore ${CDIR}/${SERVER_KS_NAME}.jks -storepass ${SERVER_KS_PWD} -alias ${SERVER_ALIAS} -keyalg RSA -dname "${DNAME}"

# Export server certificate in raw
keytool -exportcert -keystore ${CDIR}/${SERVER_KS_NAME}.jks -storepass ${SERVER_KS_PWD} -alias ${SERVER_ALIAS} -file ${CDIR}/${SERVER_KS_NAME}.crt

# Import server certificate into JKS
keytool -import -keystore ${CDIR}/${CLIENT_KS_NAME}.jks -storepass ${CLIENT_KS_PWD} -alias ${CLIENT_ALIAS} -file ${CDIR}/${SERVER_KS_NAME}.crt

# Verify server's JKS
echo "--------------------------------------------------------------------------------"
echo -e "\n\n\tVerifying Server's Keystore: ${SERVER_KS_NAME}.jks\n\n"
echo "--------------------------------------------------------------------------------"
keytool -list -v -keystore ${CDIR}/${SERVER_KS_NAME}.jks -storepass ${SERVER_KS_PWD}

# Verify client's JKS
echo "--------------------------------------------------------------------------------"
echo -e "\n\n\tVerifying Client's Keystore: ${CLIENT_KS_NAME}.jks\n\n"
echo "--------------------------------------------------------------------------------"
keytool -list -v -keystore ${CDIR}/${CLIENT_KS_NAME}.jks -storepass ${CLIENT_KS_PWD}
