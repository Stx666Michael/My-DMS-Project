#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main()
{
	int i, status;
	pid_t pid;
	for(i=0;i<4;i++)
	{
		pid = fork();
		if(pid == 0)
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
	waitpid(pid, &status, WUNTRACED);
	printf("Bye from the parent!\n");
}
