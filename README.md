# QuizCampus
Aplicació per respondre enquestes a la web.

#Descripció general
Aquest projecte consisteix en el desenvolupament d'una aplicació per respondre enquestes a la web.
La llista de enquestes disponibles és gestionada per un administrador.
Qualsevol usuari registrat pot respondre les enquestes.
#Requeriments
* Usuari de l'aplicació pot ser qualsevol, només cal registrar-se primer per accedir.
* El primer usuari registrat serà l'administrador
* No serà possible eliminar l'últim administrador de l'aplicació
* Un administrador pot promoure un usuari a administrador.
* Dues enquestes no poden compartir el mateix títol
* La contrasenya ha de ser validada en menys de 5 segons

#Casos d’ús
1. Registrar usuari
  * Un client ha de registrar-se a si mateix abans de consultar enquestes.
  * El procés de registre inclou la definició amb adreça de correu electrònic i contrasenya.
  * La contrasenya ha de ser confirmada per un segon camp per tal de detectar l'error d'escriure.
  * Al final del procés de registre, el client ha de poder consultar les enquestes
2. Inici de sessió
  * Qualsevol usuari per accedir a l'aplicació ha d'introduir email i contrasenya.
  * L'inici de sessió ha de ser comprovat per l'aplicació.
  * En cas de fallada, un quadre de diàleg proposa enviar un correu electrònic amb les instruccions per restablir la contrasenya.
  * En cas d'èxit, l'usuari obté accés a les funcions de l'aplicació.
3. Crear enquesta
  * L'administrador crea una enquesta indicant el títol.
4. Crear pregunta de text
  * L'administrador afegeix l'enunciat de la pregunta a l'enquesta seleccionada
  * El text pot ser llarg, fins a 500 caràcters.
  * L’aplicació valida la longitud del text de l’enunciat.
  * l'administrador estableix el valor mínim i màxim possibles per la resposta.
5. Respondre enquesta
  * Requisit: L’usuari ha d’estar registrat.
  * L'usuari selecciona una enquesta per respondre.
  * Una enquesta no pot ser resposta més d’un cop pel mateix usuari.
  * Per respondre la pregunta l'usuari indica un valor numèric
  * L’aplicació valida que el valor numèric està dintre del mínim i màxim permès.
  * Cada pregunta resposta queda guardada.
  * L’aplicació indica les preguntes que queden per respondre.
  * L’usuari pot deixar una enquesta parcialment resposta.
  * L’usuari pot modificar les respostes mentre l’enquesta no estigui confirmada.
  * L’usuari un cop respostes totes les preguntes, confirma l’enquesta i ja no es pot modificar.
  * L’usuari pot esborrar l’enquesta mentre no sigui confirmada.
6. Mostar informe de preguntes respostes
  * L'administrador pot veure un informe amb les valoracions numèriques realitzades per cada pregunta.
7. Mostrar respostes realitzades
  * L'usuari tria una enquesta realitzada per veure el resum de les respostes donades.
  * L’informe resum mostra quantes respostes coincideixen amb la valoració donada.
