#include <stdio.h>
int scelta;
int primoNumero, secondoNumero, risultato;
void mostraMenu() {
printf("%s", "0. Termina\n");
printf("%s", "1. Somma\n");
printf("%s", "2. Moltiplicazione\n");
printf("%s", "3. Divisione\n");
printf("%s", "4. Elevamento a potenza\n");
printf("%s", "5. Fibonacci\n");
}
void leggiNumeri(int *primoNumero, int *secondoNumero) {
printf("%s", "Inserisci primo numero intero: ");
scanf("%d", &*primoNumero);
printf("%s", "Inserisci secondo numero intero: ");
scanf("%d", &*secondoNumero);
}
void leggiNumeriPositivi(int *primoNumero, int *secondoNumero) {
*primoNumero = 0;
while ((*primoNumero <= 0)) {
printf("%s", "Inserisci primo numero intero positivo: ");
scanf("%d", &*primoNumero);
}
*secondoNumero = 0;
while ((*secondoNumero <= 0)) {
printf("%s", "Inserisci secondo numero intero positivo: ");
scanf("%d", &*secondoNumero);
}
}
void leggiNumeroPositivo(int *numero) {
*numero = 0;
while ((*numero <= 0)) {
printf("%s", "Inserisci numero intero positivo: ");
scanf("%d", &*numero);
}
}
void somma(int primo, int secondo, int *risultato) {
*risultato = (primo + secondo);
}
void moltiplicazione(int primo, int secondo, int *risultato) {
int i = 0;
int n = secondo;
if ((secondo < 0)) {
n = (-secondo);
}
*risultato = 0;
while ((i < n)) {
*risultato = (*risultato + primo);
i = (i + 1);
}
if ((secondo < 0)) {
*risultato = (-*risultato);
}
}
void divisione(int primo, int secondo, int *risultato) {
*risultato = (primo / secondo);
}
void potenza(int base, int esp, int *risultato) {
int i = 0;
*risultato = 1;
while ((i < esp)) {
*risultato = (*risultato * base);
i = (i + 1);
}
}
void fibonacci(int numero, int *risultato) {
int a, b, c, i;
if ((numero <= 0)) {
*risultato = 0;
} else {
if ((numero == 1)) {
*risultato = 1;
} else {
a = 0;
b = 1;
i = 2;
while ((i < numero)) {
c = (a + b);
a = b;
b = c;
i = (i + 1);
}
*risultato = c;
}
}
}
int main() {
printf("%s", "Benvenuto!\n");
mostraMenu();
scelta = (-1);
while ((!(scelta == 0))) {
printf("%s", "Scelta: ");
scanf("%d", &scelta);
if (((scelta > 0) || (scelta < 6))) {
if ((scelta == 1)) {
leggiNumeri(&primoNumero, &secondoNumero);
somma(primoNumero, secondoNumero, &risultato);
} else {
if ((scelta == 2)) {
leggiNumeri(&primoNumero, &secondoNumero);
moltiplicazione(primoNumero, secondoNumero, &risultato);
} else {
if ((scelta == 3)) {
leggiNumeriPositivi(&primoNumero, &secondoNumero);
divisione(primoNumero, secondoNumero, &risultato);
} else {
if ((scelta == 4)) {
leggiNumeriPositivi(&primoNumero, &secondoNumero);
potenza(primoNumero, secondoNumero, &risultato);
} else {
if ((scelta == 5)) {
leggiNumeroPositivo(&primoNumero);
fibonacci(primoNumero, &risultato);
}
}
}
}
}
printf("%d%s", risultato, "\n");
}
}
printf("%s", "Arrivederci!");
}
