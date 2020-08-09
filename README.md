# fast-message-protocol
Fast Message Protocol (FMP). Very simple and fast protocol originally realized on java.

### Example: 
Client: get

Client: 0

Server: "{\"sender\":\"1\",\"message\":\"This is a test message!\"}"

Connection ends.

## send
Send a message to server. You need to specify destination id,
### Example:

Client: send

Client: 1

Client: This is an another test message

Client: 0

Connection ends.

# And how about message encrypting?
This is client problem, you can send encrypted message after exchanging public keys (RSA for example). But you also can send an unencrypted message, any client of this instance will able to read it.
# Libs
## org.json 20160810
## mysql-connector-java 8.0.21
