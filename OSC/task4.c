#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

long int getDiffInMicSec(struct timeval start, struct timeval end)
{
	int seconds = end.tv_sec - start.tv_sec;
	int useconds = end.tv_usec - start.tv_usec;
	int mtime = (seconds * 1000000 + useconds);
	return mtime;
}

int main()
{
	int pid;
	struct timeval startTime, currentTime;

	for(int i=0;i<4;i++)
	{
		gettimeofday(&startTime, NULL);
		pid = fork();
		gettimeofday(&currentTime, NULL);
		if(pid == 0)
		{
			printf("Hello from child process %d with pid %d at time %d\n",i,getpid(),getDiffInMicSec(startTime,currentTime));
			exit(0);
		}
	}
}
//Thank you!