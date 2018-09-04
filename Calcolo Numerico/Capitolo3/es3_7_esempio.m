% Viene generata la matrice 
A = toeplitz_Generator(12,100);
condizionamento = cond(A,2);

% primo caso
b = [1;101;101;101;101;101;101;101;101;101;101;101];
xPrimoCaso = sistema_Toeplitz(A,b);

% secondo caso
b = [1;101;101;101;101;101;101;101;101;101;101;101];
b = b * 0.1
xSecondoCaso = sistema_Toeplitz(A,b);