#include <stdio.h>
#include <stdlib.h>

int main()
{
	for(int i=0;i<4;i++)
	{
		if(fork() == 0)
		{
			sleep(1);
			printf("Hello from child process %d with pid %d\n",i,getpid());
			exit(0);
		}
		else
		{
			printf("Hello from parent process with pid %d\n",getpid());
		}
	}
	if(fork() == 0)
	{
		execl("/bin/ps", "ps", "l", 0);
	}
}
