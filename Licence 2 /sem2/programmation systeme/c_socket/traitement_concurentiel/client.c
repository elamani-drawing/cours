#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <errno.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <sys/unistd.h>

void main()
{
    int ret;
    int i;
    int sd;
    char msg[128];
    struct sockaddr_un source, cible;
    int fromlen = sizeof(source);
    cible.sun_family = AF_UNIX;
    strcpy(cible.sun_path, "serv_sock");
    if ((sd = socket(AF_UNIX, SOCK_DGRAM, 0)) != -1)
        printf("socket = %d\n", sd);
    else
        perror("Erreur socket");
    for (i = 0; i < 2; i++)
    {
        sprintf(msg, "%d\t%d", getpid(), i);
        if ((ret = sendto(sd, msg, strlen(msg), 0, (const struct sockaddr *)&cible, sizeof(cible))) == -1)
            perror("Erreur sendto");
    }
}
