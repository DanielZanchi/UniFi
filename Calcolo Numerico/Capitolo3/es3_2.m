A1 = [1 -1 2 2; -1  5  -14 2; 2 -14 42 2; 2 2 2 65];
A2 = [1 -1 2 2; -1 6 -17 3; 2 -17 48 -16; 2 3 -16 4];

algoritmo_A1 = algoritmo36(A1);
algoritmo_A2 = algoritmo36(A2);

function [A] = algoritmo36(A)
    [m,n]=size(A);
    if A(1,1)<=0
       error('matrice non sdp');
    end
    A(2:n,1) = A(2:n,1)/A(1,1);
    for j = 2:n
        v = ( A(j,1:(j-1))') .* diag(A(1:(j-1),1:(j-1)));
        A(j,j) = A(j,j)-A(j,1:(j-1))*v;
        if A(j,j)<=0
            error('matrice non sdp');
        end
        A((j+1):n,j)=(A((j+1):n,j)-A((j+1):n,1:(j-1))*v)/A(j,j);
    end
end
