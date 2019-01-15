#include <stdio.h>
int a, b, c;
int main() {
scanf("%d%d%d", &a, &b, &c);
printf("%c%d%c", '\t', ((-a) + (-(((-2) * b) / c))), '\n');
if ((((a >= b) && (a <= 12)) || (!(c == 12)))) {
printf("%s", "Ramo Then");
} else {
printf("%s", "Ramo Else");
}
}
