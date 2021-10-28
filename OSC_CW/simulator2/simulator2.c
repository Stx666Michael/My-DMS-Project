#include <stdio.h>
#include "coursework.h"
#include "linkedlist.h"

int main()
{
	LinkedList readyQueue = LINKED_LIST_INITIALIZER;
	LinkedList termiQueue = LINKED_LIST_INITIALIZER;
	struct timeval startTime, currentTime;

	gettimeofday(&startTime, NULL);

	for (int i=0; i<NUMBER_OF_PROCESSES; i++) {
		Process * p = generateProcess(i);
		addLast(p, &readyQueue);
	}

	while (getHead(readyQueue) != NULL) {
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

	while (getHead(termiQueue) != NULL) {
		removeFirst(&termiQueue);
	}

	gettimeofday(&currentTime, NULL);
	printf("Difference in milli-seconds %d\n", getDifferenceInMilliSeconds(startTime, currentTime));

	return 0;
}