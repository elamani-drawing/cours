#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <sys/unistd.h>

void main(int argc, char **argv)
{
    //localhost || kali
    int ret;
    int sd;
    char msg[128];
    struct sockaddr_in source, cible;
    struct hostent *hp;
    int fromlen = sizeof(source);
    //récupère la structure hostent de la machine distante dont le nom est passé en paramètre de ligne de commande (argv[1]).
    if ((hp = gethostbyname(argv[1])) == NULL)
        perror("Erreur gethostbyname");
    //initialise la structure cible
    bzero((char *)&cible, sizeof(cible));
    bcopy(hp->h_addr, (char *)&cible.sin_addr, hp->h_length);
    cible.sin_family = hp->h_addrtype;
    cible.sin_port = htons(2000);
    //crée la socket locale
    if ((sd = socket(AF_INET, SOCK_DGRAM, 0)) != -1)
        printf("socket = %d\n", sd);
    else
        perror("Erreur socket");
    for (;;)
    {
        //invite l'utilisateur à introduire un message (texte) qui sera stocké dans msg
        fprintf(stdout, "Entrer message : ");
        scanf("%s", msg);
        if ((ret = sendto(sd, msg, strlen(msg), 0, (const struct sockaddr *)&cible, sizeof(cible))) != -1)
            fprintf(stdout, "\tsendto = %d; msg = %s\n", ret, msg);
        else
            perror("Erreur sendto");
        if ((ret = recvfrom(sd, msg, sizeof(msg), 0, (struct sockaddr *)&source, &fromlen)) != -1)
        {
            msg[ret] = '\0';
            fprintf(stdout, "\t\trecvfrom = %d; msg = %s\n", ret, msg);
        }
        else
            perror("Erreur recvfrom");
    }
}