#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>

int main() {
    //initialise la structure cible (distante)
    int ret; int sd; char msg[128]; struct sockaddr_un locale, cible;
    cible.sun_family = AF_UNIX; strcpy(cible.sun_path, "serv_sock");
    //crée la socket locale
    if ((sd = socket(AF_UNIX, SOCK_STREAM, 0)) != -1) 
        printf("socket = %d\n", sd);
    else { 
        ("Erreur socket"); 
        exit(-1); 
    }
    //demande la connexion de sa socket locale à la socket cible (distante)
    if (connect(sd, (const struct sockaddr *) &cible, sizeof(cible)) == -1) {
            perror("Erreur connect"); exit(-1); 
        } 
    for (;;) {
        //invite l'utilisateur à introduire un message(texte) qui sera stocké dans msg
        printf("Entrer message : "); 
        scanf("%s", msg);
        //émission du message
        if ((ret = send(sd, msg, strlen(msg), 0)) != -1)
            fprintf(stdout, "\tsend = %d; msg = %s\n", ret, msg);
        else perror("Erreur send");
        //réception d'un message (réponse)
        if ((ret = recv(sd, msg, sizeof(msg), 0)) != -1) {
            msg[ret] = '\0'; fprintf(stdout, "\t\trecv = %d; msg = %s\n", ret, msg); 
        }
        else perror("Erreur recv");
    }
}