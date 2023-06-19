#include <stdio.h>
//#include <windows.h>

int main(int argc, char *argv[]) {

  char *file_name = "meu_arquivo.txt";

  FILE *arquivo = fopen(file_name, "w");

  if (arquivo == NULL) {
    return 1;
  }

  for (int i = 0; i < 1200; i++) {
    fprintf(arquivo, "%d\n", i+1);
  }

  fclose(arquivo);

  arquivo = fopen(file_name, "r");

  if (arquivo == NULL) {
    return 1;
  }

  int num;

  while (fscanf(arquivo, "%d", &num) != EOF) {
//    Sleep(1);
    printf("%d\n", num);
  }

  fclose(arquivo);

  return 0;
}
