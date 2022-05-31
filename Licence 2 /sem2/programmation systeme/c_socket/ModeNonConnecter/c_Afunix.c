#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

void main()
{
    int ret;
    int sd;
    char msg[128];
    struct sockaddr_un locale, source, cible;
    //initialise la structure locale
    int fromlen = sizeof(source);
    locale.sun_family = AF_UNIX;
    strcpy(locale.sun_path, "cli_sock");
    cible.sun_family = AF_UNIX;
    strcpy(cible.sun_path, "serv_sock");
    if ((sd = socket(AF_UNIX, SOCK_DGRAM, 0)) != -1)
        printf("socket = %d\t", sd);
    else
        perror("Erreur socket");
    //nommage de la socket locale
    if ((ret = bind(sd, (const struct sockaddr *)&locale, sizeof(locale))) != -1)
        printf("bind = %d\n", ret);
    else
        perror("Erreur bind");

    for (;;)
    {
        // invite l'utilisateur à introduire un message (texte) qui sera stocké dans msg
        printf("Entrer message : ");
        scanf("%s", msg);
        //émission du message
        if ((ret = sendto(sd, msg, strlen(msg), 0, (const struct sockaddr *)&cible, sizeof(cible))) != -1)
            fprintf(stdout, "\tsendto = %d; msg = %s\n", ret, msg);
        else
            perror("Erreur sendto");
        // réception d'un message (réponse)
        if ((ret = recvfrom(sd, msg, sizeof(msg), 0, (struct sockaddr *)&source, &fromlen)) != -1)
        {
            msg[ret] = '\0';
            fprintf(stdout, "\t\trecvfrom = %d; msg = %s\n", ret, msg);
        }
        else
            perror("Erreur recvfrom");
    }
}
