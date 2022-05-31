#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <errno.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <sys/unistd.h>

//s'exécute sur réception du signal SIGINT, et supprime le nom de la socket locale
void interrupt(int signo)
{
  unlink("serv_sock");
  exit(0);
}
void main()
{
  int ret;
  int sd;
  char msg[128];
  struct sockaddr_un locale, source, cible;
  int fromlen = sizeof(source);
  //capte le signal SIGINT
  signal(SIGINT, interrupt);
  //initialise la structure locale
  locale.sun_family = AF_UNIX;
  strcpy(locale.sun_path, "serv_sock");
  //crée la socket locale
  if ((sd = socket(AF_UNIX, SOCK_DGRAM, 0)) != -1)
    printf("socket = %d\t", sd);
  else
    ("Erreur socket");
  //nomme la socket locale
  if ((ret = bind(sd, (const struct sockaddr *)&locale, sizeof(locale))) != -1)
    printf("bind = %d\n", ret);
  else
    perror("Erreur bind");
  for (;;)
  {
    //emmission reception
    if ((ret = recvfrom(sd, msg, sizeof(msg), 0, (struct sockaddr *)&source, &fromlen)) != -1)
    {
      msg[ret] = '\0';
      fprintf(stdout, "\trecvfrom = %d; msg = %s\n", ret, msg);
    }
    else
      perror("Erreur recvfrom");
    cible = source;
    if ((ret = sendto(sd, msg, strlen(msg), 0, (const struct sockaddr *)&cible, sizeof(cible))) != -1)
      fprintf(stdout, "\t\tsendto = %d; msg = %s\n", ret, msg);
    else
      perror("Erreur sendto");
  }
}
