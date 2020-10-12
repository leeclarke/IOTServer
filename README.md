# IOTServer

A simple TCP based IOT server what can process incoming IOT data and persist to a verity of data stores. While flexibility is a primary goal an attitude of local first will take residence over cloud dependence. In short, you can run this off-grid if you need to!

Note: this is a work in progress, what's here runs and can be used but will be evolving over time.
Targeted DataStores: 
* Sqlite - Local
* Adafruit IO
* Prometheus
* AWS DynamoDB or DocumentDB 
* maybe more...

## Compact Message Format
The initial protocol for communications will be a compact, fixed-length format that keeps messages under 256 bytes of data. This small format is intended to support small sensor devices or even loRa transmission of data.

|Compact Message Format                                                                    |
|------------------------------------------------------------------------------------------|
|name          | length | format        | Notes                                            |
|------------------------------------------------------------------------------------------|
|type          | 2      | 00            | 00 - id for compact format                       |
|feed-id       | 11     | 00000000000   |                                                  |
|created-date  | 14     | yyyyMMddhhmmss| UTC * if empty server time will be inserted      |
|lat           | 9      | 000000000     | 1st digit is neg val indicator                   |
|lon           | 9      | 000000000     | last 5 are decimal, 3 whole numbers              |
|ele           | 11     | 00000000000   | optional pass blank or pad 0s last 3 are decimal |
|value         | 196    |               | free text                                        |
|------------------------------------------------------------------------------------------|
|lat|lon spec and examples  first digit indicates a negative value id == 1                 |
|To have a predictable fixed length format there are a few rules to sending values.        |
|Examples:                                                                                 |
|value      | send format|                                                                 |
|-1.12      | 100112000  |                                                                 |
|20.123     | 002012300  |                                                                 |
|32.30642   | 003230642  |                                                                 |
|-122.61458 | 112261458  |                                                                 |
|------------------------------------------------------------------------------------------|
|ele: negative values same as lat/lon                                                      |
|can be left blank.  if used it must be padded with zeros. Not last 3 are decimal amounts  |
|Example:                                                                                  |
|value       | format                                                                      |
|nothing     | BLANK                                                                       |
|10          | 00000010000                                                                 |
|.210        | 00000000210                                                                 |
|-213.10     | 10000213100                                                                 |
|1242.742    | 00001242742                                                                 |
------------------------------------------------------------------------------------------|

## Run it
Run IOTServer with  argument for port
Run one of the Test Clients to send a test string like the following:
`00000002304022020060615330100323064211226145800003245100Hello World                                                                                                                                                                                                    `