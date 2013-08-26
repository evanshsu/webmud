WebMud
----------------------
Play MUD in Browser with Your Own Proxy.

## Sample Site: http://mud.twbbs.org/kk

## Install on your server:
  1. Download Source Code Zip and Unzip
  2. Go to folder: booter/bin
  3. sudo sh runkk.sh
  4. Go to http://[your ip address]/kk

## Customize for other MUDs:
  1. open src/mud.properties in webmud.war
  2. add new config for new MUD, ex: doom
```js
  doom.url=140.126.11.213
  doom.port=4000
  doom.encoding=MS950
```
