#include <errno.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

// s'exécute sur réception du signal SIGINT, et supprime le nom de la socket
// locale
void interrupt(int signo) {
  unlink("serv_sock");
  exit(0);
}

void main() {
  int ret;
  int sd, newsd;
  char msg[128];
  char name[128];
  struct sockaddr_in locale, source;
  struct hostent *hp;
  int fromlen = sizeof(source);
  // initialise la structure locale
  //récupère dans name le nom de la machine locale
  if (gethostname(name, sizeof(name)) != 0) {
    perror("Erreur gethostname");
    exit(-1);
  }
  printf("le nom : %s", name);
  if ((hp = gethostbyname(name)) == NULL) {
    perror("Erreur gethostbyname");
    exit(-1);
  }
  bzero((char *)&locale, sizeof(locale));
  bcopy(hp->h_addr, (char *)&locale.sin_addr, hp->h_length);
  locale.sin_family = hp->h_addrtype;
  locale.sin_port = ntohs(2000);
  // crée et nomme la socket locale
  if ((sd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
    perror("Erreur socket");
    exit(-1);
  }
  if (bind(sd, (const struct sockaddr *)&locale, sizeof(locale)) == -1) {
    perror("Erreur bind");
    exit(-1);
  }
  // met la socket locale en état d'écoute
  if (listen(sd, 5) != 0) {
    perror("Erreur listen");
    exit(-1);
  }
  for (;;) {
    // acceptation de connexion, sinon attente de demande
    if ((newsd = accept(sd, (struct sockaddr *)&source, &fromlen)) == -1) {
      perror("Erreur accept");
      exit(-1);
    }
    for (;;) {
      //reception dun message
      if ((ret = recv(newsd, msg, sizeof(msg), 0)) != 0) {
        msg[ret] = '\0';
        fprintf(stdout, "\trecv = %d; msg = %s\n", ret, msg);
      } else {
        printf("Connexion coupée !\n");
        break;
      }
      //ajout du texte " traitée !" à la fin du message reçu (msg)
      strcat(msg, " traitée !");
      //émission d'une réponse (message reçu + texte " traitée !")
      if ((ret = send(newsd, msg, strlen(msg), MSG_NOSIGNAL)) != -1)
        fprintf(stdout, "\t\tsend = %d; msg = %s\n", ret, msg);
      else
        perror("Erreur send");
    }
    close(newsd);
  }
}