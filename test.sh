rm -f *.log
/usr/bin/env /usr/lib/jvm/java-17-openjdk-amd64/bin/java @/tmp/cp_4pwmo5r294wjtqyeigbz7ftsq.argfile es.etg.dam.psp.cliente.Cliente Jugador1 > jugador1.log 2>&1 & 
/usr/bin/env /usr/lib/jvm/java-17-openjdk-amd64/bin/java @/tmp/cp_4pwmo5r294wjtqyeigbz7ftsq.argfile es.etg.dam.psp.cliente.Cliente Jugador2 > jugador2.log 2>&1 &
/usr/bin/env /usr/lib/jvm/java-17-openjdk-amd64/bin/java @/tmp/cp_4pwmo5r294wjtqyeigbz7ftsq.argfile es.etg.dam.psp.cliente.Cliente Jugador3 > jugador3.log 2>&1 &
/usr/bin/env /usr/lib/jvm/java-17-openjdk-amd64/bin/java @/tmp/cp_4pwmo5r294wjtqyeigbz7ftsq.argfile es.etg.dam.psp.cliente.Cliente Jugador4 > jugador4.log 2>&1 &
wait
cat *.log