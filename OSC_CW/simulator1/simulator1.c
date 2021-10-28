#include <stdio.h>
#include "coursework.h"

int main()
{
	Process * p = generateProcess(0);
	runPreemptiveProcess(p, 0);
	printf("SIMULATING: [PID = %d, BurstTime = %d, RemainingBurstTime = %d]\n", p->iPID, p->iBurstTime, p->iRemainingBurstTime);
	return 0;
}