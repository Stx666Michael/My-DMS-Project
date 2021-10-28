#include <stdio.h>
#include <pthread.h>
#include "coursework.h"
#include "linkedlist.h"

int terminatedProcess = 0;
LinkedList readyQueue = LINKED_LIST_INITIALIZER;
LinkedList termiQueue = LINKED_LIST_INITIALIZER;

void* processGenerator()
{
	for (int i=0; i<NUMBER_OF_PROCESSES; i++) {
		Process * p = generateProcess(i);
		addLast(p, &readyQueue);
		//printf("i = %d\n", i);
		while (i+1-terminatedProcess >= MAX_CONCURRENT_PROCESSES);
	}
}

void* processSimulator()
{
	while (terminatedProcess != NUMBER_OF_PROCESSES) {
		while (getHead(readyQueue) == NULL);
		Process * p = getHead(readyQueue) -> pData;
		runPreemptiveProcess(p, 0);
		printf("SIMULATING: [PID = %d, BurstTime = %d, RemainingBurstTime = %d]\n", p->iPID, p->iBurstTime, p->iRemainingBurstTime);
		
		if (p->iStatus == READY) {
			addLast(p, &readyQueue);
		} else if (p->iStatus == TERMINATED) {
			addLast(p, &termiQueue);
		}
		removeFirst(&readyQueue);
	}
}

void* termiDaemon()
{
	while (terminatedProcess != NUMBER_OF_PROCESSES) {
		while (getHead(termiQueue) == NULL);
		removeFirst(&termiQueue);
		terminatedProcess++;
		//printf("%d\n", terminatedProcess);
	}
}

int main()
{
	pthread_t thread1;
 	pthread_t thread2;
 	pthread_t thread3;
 	struct timeval startTime, currentTime;

 	gettimeofday(&startTime, NULL);

	pthread_create(&thread1, NULL, &processGenerator, NULL);
	pthread_create(&thread2, NULL, &processSimulator, NULL);
	pthread_create(&thread3, NULL, &termiDaemon, NULL);

	pthread_join(thread1, NULL);
	pthread_join(thread3, NULL);

	gettimeofday(&currentTime, NULL);
	printf("Difference in milli-seconds %d\n", getDifferenceInMilliSeconds(startTime, currentTime));

	return 0;
}