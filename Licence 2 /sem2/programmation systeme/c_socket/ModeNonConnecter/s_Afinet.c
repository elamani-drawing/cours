#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <sys/unistd.h>
#include <stdio.h>
#include <errno.h>
#include <netdb.h>
#include <netinet/in.h>

void main()
{
  int ret;
  int sd;
  char msg[128];
  char name[128];
  struct sockaddr_in locale, source, cible;
  struct hostent *hp;
  int fromlen = sizeof(source);
  //récupère dans name le nom de la machine locale
  if (gethostname(name, sizeof(name)) != 0)
    perror("Erreur de gethostname");
  //récupère la structure hostent de la machine locale
  printf("--le nom %s", name);
  if ((hp = gethostbyname(name)) == NULL)
    perror("Erreur gethostbyname");
  bzero((char *)&locale, sizeof(locale));
  bcopy(hp->h_addr, (char *)&locale.sin_addr, hp->h_length);
  locale.sin_family = hp->h_addrtype;
  locale.sin_port = htons(2000);
  //crée la socket locale
  if ((sd = socket(AF_INET, SOCK_DGRAM, 0)) != -1)
    printf("socket = %d\t", sd);
  else
    perror("Erreur socket");
  //nomme la socket locale
  if ((ret = bind(sd, (const struct sockaddr *)&locale, sizeof(locale))) != -1)
    printf("bind = %d\n", ret);
  else
    perror("Erreur bind");
  for (;;)
  {
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