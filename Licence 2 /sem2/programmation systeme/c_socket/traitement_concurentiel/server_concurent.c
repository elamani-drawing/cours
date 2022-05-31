#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <errno.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <sys/unistd.h>

void interrupt(int signo)
{
    unlink("serv_sock");
    exit(0);
}
void main()
{
    //delaisse le traiement à un fork
    int ret;
    int sd;
    char msg[128];
    char pid[16];
    char req[16];
    struct sockaddr_un locale, source, cible;
    int fromlen = sizeof(source);
    signal(SIGINT, interrupt);
    locale.sun_family = AF_UNIX;
    strcpy(locale.sun_path, "serv_sock");
    if ((sd = socket(AF_UNIX, SOCK_DGRAM, 0)) == -1)
        perror("Erreur socket");
    if ((ret = bind(sd, (const struct sockaddr *)&locale, sizeof(locale))) == -1)
        perror("Erreur bind");
    for (;;)
    {
        if ((ret = recvfrom(sd, msg, sizeof(msg), 0, (struct sockaddr *)&source, &fromlen)) == -1)
            perror("Erreur recvfrom");
        else
        {
            if (fork() == 0)
            {
                sscanf(msg, "%s%s", pid, req);
                printf("Début traitement requête %s de %s par %d\n", req, pid, getpid());
                sleep(5);//simule le temps de traitement de la requête
                printf("\tFin traitement requête %s de %s par %d\n", req, pid, getpid());
            }
        }
    }
}