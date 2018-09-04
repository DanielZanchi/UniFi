function [b] = sistema_Toeplitz(A, b)
% [b] = sistema_Toeplitz(A, b)
% Calcola la soluzione di Ax=b con A matrice bidiagonale inferiore a
% diagonale unitaria di Toeplitz
% A Matrice
% b vettore dei termini noti
% [b] soluzione
[n, m] = size(A);
if n~=m
    error('matrice non quadrata');
end
for i=2:n
    j=i-1
    b(i) = b(i) - A(i,j)*b(j);
    b(i) = b(i)/A(i,i);
end
end

function [A] = toeplitz_Generator(n, alfa)
% [A] = toeplitz_Generator(n, alfa)
% Genera una matrice Toeplitz
% n dimenstione matirce
% alfa valore della sottodiagonale
% [A] matrice di toeplitz
A(1,1) = 1;
for i=2:n
    A(i,i-1) = alfa;
    A(i,i) = 1;
end
end
