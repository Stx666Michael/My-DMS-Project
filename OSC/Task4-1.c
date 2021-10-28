#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <pthread.h>
#define ROWS 400
#define COLUMNS 10000

int aiMatrix[ROWS][COLUMNS];
int iSum = 0;

long int getDiffInMicSec(struct timeval start, struct timeval end)
{
	int seconds = end.tv_sec - start.tv_sec;
	int useconds = end.tv_usec - start.tv_usec;
	int mtime = (seconds * 1000000 + useconds);
	return mtime;
}

void init() {
	int iRow, iColumn;
	int cont = 0;
	for(iRow = 0; iRow < ROWS; iRow++){
		for(iColumn = 0; iColumn < COLUMNS; iColumn++) {
			cont++;
			aiMatrix[iRow][iColumn] = cont;
		}
	}
}

void* calculateSum(int i){
	int iRow, iColumn;
	int sum=0;

	for(iRow = i*(ROWS/2); iRow < (i+1)*(ROWS/2); iRow++) {
		for(iColumn = 0; iColumn < COLUMNS; iColumn++) {
			sum += aiMatrix[iRow][iColumn];
		}
	}
	return (void*) sum;	      
}

int CalculateSum(){
	int iRow, iColumn;
	int sum=0;

	for(iRow = 0; iRow < ROWS; iRow++) {
		for(iColumn = 0; iColumn < COLUMNS; iColumn++) {
			sum += aiMatrix[iRow][iColumn];
		}
	}
	return sum;	      
}

int main(){
	init();
	int sum1, sum2;
	pthread_t thread1_id;
	pthread_t thread2_id;
	struct timeval startTime, currentTime;

	gettimeofday(&startTime, NULL);
	//pthread_create(&thread1_id, NULL, &calculateSum, 0);
	//pthread_create(&thread2_id, NULL, &calculateSum, 1);
	//pthread_join (thread1_id, (void*) &sum1);
	//pthread_join (thread2_id, (void*) &sum2);
	//iSum = sum1 + sum2;
	iSum = CalculateSum();
	gettimeofday(&currentTime, NULL);

	printf("sum = %d, time = %d ms\n", iSum, getDiffInMicSec(startTime,currentTime));
	
	return 0;
}
