#include <sys/types.h> 
#include <errno.h>
#include <sys/socket.h> 
#include <netdb.h>
#include <stdio.h> 
#include <netinet/in.h>
#include <stdlib.h> 
#include <string.h>
#include <unistd.h>

void main(int argc, char **argv)
{
    //./c.exe kali
    int ret;
    int sd;
    char msg[128];
    struct sockaddr_in locale, cible;
    struct hostent *hp;
    //récupère la structure hostent de la machine distante dont le nom est passé en paramètre de ligne de commande (argv[1]).
    if ((hp = gethostbyname(argv[1])) == NULL)
    {
        perror("Erreur gethostbyname");
        exit(-1);
    }
    // initialise la structure cible (distante)
    bzero((char *)&cible, sizeof(cible));
    bcopy(hp->h_addr, (char *)&cible.sin_addr, hp->h_length);
    cible.sin_family = hp->h_addrtype;
    cible.sin_port = htons(2000);
    if ((sd = socket(AF_INET, SOCK_STREAM, 0)) == -1)
    {
        perror("Erreur socket");
        exit(-1);
    }
    if (connect(sd, (const struct sockaddr *)&cible, sizeof(cible)) == -1)
    {
        perror("Erreur connect");
        exit(-1);
    }
    for (;;)
    {
        printf("Entrer message : ");
        scanf("%s", msg);
        if ((ret = send(sd, msg, strlen(msg), 0)) != -1)
            fprintf(stdout, "\tsend = %d; msg = %s\n", ret, msg);
        else
            perror("Erreur send");
        if ((ret = recv(sd, msg, sizeof(msg), 0)) != -1)
        {
            msg[ret] = '\0';
            fprintf(stdout, "\t\trecv = %d; msg = %s\n", ret, msg);
        }
        else
            perror("Erreur recv");
    }
}
