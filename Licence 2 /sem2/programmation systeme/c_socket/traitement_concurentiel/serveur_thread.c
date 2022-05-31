#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

typedef struct User
{
  char nom[30];
  int age;
} User;

void *function(void *arg)
{
  int socket = *(int*) arg;
  char msg[] = "quel est votre nom et votre age?";
  User user;

  send(socket, msg, strlen(msg)+1, 0);
  recv(socket, &user, sizeof(user),0);
  printf("Le client sappelle %s et a %d ans \n", user.nom, user.age);
  close(socket);
  free(arg);
  pthread_exit(NULL);
}

int main(void) {
  //pthread_t clientThread;
  //creation dune socket ipv4
  int socketServer = socket(AF_INET, SOCK_STREAM, 0);
  struct sockaddr_in addrServer;
  //ecoute
  addrServer.sin_addr.s_addr = inet_addr("127.0.0.1");
  addrServer.sin_family = AF_INET;
  addrServer.sin_port = htons(30000);
  //nomme la socket
  bind(socketServer, (const struct sockaddr *)&addrServer, sizeof(addrServer));
  printf("bind : %d \n", socketServer);
  //maximum 5 connexions
  listen(socketServer, 5);
  printf("listen\n");
  pthread_t threads[3];
  //creation dun socket pour le client
  for(int i = 0; i<3; i++)
  {
    struct sockaddr_in addrClient;
    socklen_t csize = sizeof(addrClient);
    int socketCLient = accept(socketServer, (struct sockaddr *)&addrClient, &csize);
    printf("accept \n");

    printf("client: %d\n", socketCLient);
    /*
    User user = {
      .nom = "Arthur", 
      .age = 10
    };*/

    int *arg = malloc(sizeof(int));
    *arg = socketCLient;
    pthread_t *clientThread = malloc(sizeof(pthread_t));
    pthread_create(&threads[i], NULL, function, arg);
  }
  for(int i = 0; i<3;i++)
  {
    pthread_join(threads[i], NULL);
  }
  close(socketServer);
  printf("fermeture");
  return 0;
}