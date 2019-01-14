#include <stdio.h>
int scelta = 1;
double primoNumero, secondoNumero, risultato;
char stringa[];
int esse, esse2;
void mostraMenu() {
printf("%s", "0. Termina\n");
printf("%s", "1. Mostra il menu\n");
printf("%s", "2. Somma\n");
printf("%s", "3. Sottrazione\n");
printf("%s", "4. Prodotto\n");
printf("%s", "5. Divisione\n");
}
void scegliOperazione(int *scelta) {
printf("%s", "Scegli l'operazione: ");
*scelta = (-1);
while (((*scelta < 0) && (*scelta > 5))) {
scanf("%d", &*scelta);
}
}
void inserisciNumeri(double *primo, double *secondo) {
printf("%s", "Inserisci il primo numero:");
scanf("%lf", &primoNumero);
printf("%s", "Inserisci il secondo numero:");
scanf("%lf", &secondoNumero);
}
void eseguiOperazione(double primo, double secondo, int operazione, double *risultato) {
double res = 0;
if ((operazione == 2)) {
res = (primoNumero + secondoNumero);
} else {
if ((operazione == 3)) {
res = (primoNumero - secondoNumero);
} else {
if ((operazione == 4)) {
res = (primoNumero - secondoNumero);
} else {
if ((operazione == 5)) {
if ((!(secondoNumero == 0))) {
res = (primoNumero / secondoNumero);
}
}
}
}
}
*risultato = res;
}
int main() {
while ((!(scelta == 0))) {
if ((scelta == 1)) {
mostraMenu();
} else {
inserisciNumeri(primoNumero, secondoNumero);
eseguiOperazione(primoNumero, secondoNumero, scelta, risultato);
printf("%lf", risultato);
}
scegliOperazione(scelta);
}
printf("%s%s", "Arriverderci!", "\nBuona giornata");
}
