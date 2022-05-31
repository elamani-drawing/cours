#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <signal.h>
#include <unistd.h>

//s'exécute sur réception du signal SIGINT, et supprime le nom de la socket locale
void interrupt(int signo) {
    unlink("serv_sock"); 
    exit(0); 
}

void main() {
    int ret, sd, newsd; char msg[128];
    struct sockaddr_un locale, source; 
    int fromlen = sizeof(source);
    //capte le signal SIGINT
    signal(SIGINT, interrupt);
    //initialise la structure locale
    locale.sun_family = AF_UNIX; 
    strcpy(locale.sun_path, "serv_sock");  
    //creation de la socket locale
    if ((sd = socket(AF_UNIX, SOCK_STREAM, 0)) != -1) 
        printf("socket = %d\t", sd);
    else { 
        ("Erreur socket"); 
        exit(-1); 
    }
    //nomme la socket locale
    if ((ret = bind(sd, (const struct sockaddr *) &locale, sizeof(locale))) != -1)
        printf("bind = %d\n", ret);
    else { 
        perror("Erreur bind"); 
        exit(-1); 
    }

    //notifie que la socket locale est en état d'écoute
    if (listen(sd, 5) == -1) { 
        perror("Erreur listen"); 
        exit(-1); 
    }
    //boucle infinie sur les connexions
    for(;;) {
        //acceptation de connexion, sinon attente de demande 
        if ((newsd = accept(sd, (struct sockaddr *) &source, &fromlen)) == -1) {
            perror("Erreur accept"); exit(-1);
        }
        //boucle infinie sur les réceptions/émissions (d'une connexion)
        for (;;) {
            //réception d'un message
            if ((ret = recv(newsd, msg, sizeof(msg), 0)) != 0 ) {
                msg[ret] = '\0'; fprintf(stdout, "\trecv = %d; msg = %s\n", ret, msg); }
            else { 
                printf("Connexion coupée !\n"); 
                break; 
            }
            strcat(msg, " traitée !");//ajout du texte " traitée !" à la fin du message reçu (msg)
            //émission d'une réponse (message reçu + texte " traitée !")
            if ((ret = send(newsd, msg, strlen(msg), 0)) != -1)
                fprintf(stdout, "\t\tsend = %d; msg = %s\n", ret, msg);
            else perror("Erreur send");
        }
        close(newsd);//fermeture du descripteur de la socket de communication
    }
}