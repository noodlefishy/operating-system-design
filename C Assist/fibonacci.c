#include <stdio.h>

int r1; int r2; int r3; int r4; int r5; int r6; int r7;

int stack[1024];

void push(int value) {
	stack[r6] = value;
	r6++;
}

int pop() {
	r6--;
	return stack[r6];
}


int lessThan() {

}