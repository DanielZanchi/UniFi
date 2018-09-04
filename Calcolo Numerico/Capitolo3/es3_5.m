A = [1 -1 2 2; -1  5  -14 2; 2 -14 42 2; 2 2 2 65];

A3 = A;
A4 = A;

x3 = [5.1211 3.4433 0.1257 2.1579]';
x4 = [1.3345 2.3232 3.1175 1.6658]';

b3 = A3 * x3;
b4 = A4 * x4;

% fattorizzazione LDL^T
x3_soluzione = sistema_lineare_LDLt(algoritmo36(A3), b3);
cond_A3_2 = cond(A3, 2);
r3 = A*x3_soluzione - b3;
r_b_3 = norm(r3) / norm(b3);
err_3 = norm(x3_soluzione - x3)/norm(x3_soluzione);

% fattorizzazione LU con pivoting parziale
[A4, p4] = fatt_LUpivot(A4);
x4_soluzione = sistema_LUpivot(A4, b4, p4);
cond_A4_2 = cond(A4, 2);
r4 = A*x4_soluzione - b4;
r_b_4 = norm(r4) / norm(b4);
err_4 = norm(x4_soluzione - x4)/norm(x4_soluzione);

function [b] = sistema_lineare_LDLt(A, b)
% [b] = sistema_lineare_LDLt(A, b)
% Calcola la soluzione di Ax=b con
% A: matrice LDLt (da fattorizzazione LDLt)
% b: vettore colonna
% [b]: soluzione del sistema
    b = sistema_triang_inf(tril(A,-1)+eye(length(A)), b);
    b = diagonale(diag(A), b);
    b = sistema_triang_sup((tril(A,-1)+eye(length(A)))',b);
end

function [A, p] = fatt_LUpivot(A)
    % [A, p] = fattorizzaLUpivot(A)
    % Calcola la fattorizzazione LU della matrice A.
    % A: matrice da fattorizzare.
    % Output:
    % A: la matrice fattorizzata LU;
    % p: vettore di permutazione
    [m,n]=size(A);
    if m~=n
        error('Matrice non quadrata');
    end
    p=(1:n);
    for i=1:n-1
        [mi, ki] = max(abs(A(i:n, i)));
        if mi==0
            error('Matrice singolare');
        end
        ki = ki+i-1;
        if ki>i
            A([i ki], :) = A([ki i], :);
            p([i ki]) = p([ki i]);
        end
        A(i+1:n, i) = A(i+1:n, i)/A(i, i);
        A(i+1:n, i+1:n) = A(i+1:n, i+1:n) -A(i+1:n, i)*A(i, i+1:n);
    end
end

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

function [b]= sistema_LUpivot(A,b,p)
    % [b]= sistema_LUpivot(A,b,p)
    % Calcola la soluzione di Ax=b con A matrice LU con pivoting parziale
    % A: Matrice matrice LU con pivoting (generata da fatt_LUpivot)
    % b: vettore colonna
    % Output:
    % b: soluzione del sistema
    P = zeros(length(A));
    for i = 1:length(A)
        disp(i);
        disp(p(i));
        P(i, p(i)) = 1;
    end
    b = sistema_triang_inf(tril(A,-1)+eye(length(A)), P*b);
    b = sistema_triang_sup(triu(A), b);
end

function [d] = diagonale(d, b)
% [d] = diagonale(d, b)
% Calcolare la soluzione di Ax=b con
% d: matrice diagonale
% b: vettore colonna
% [d]: soluzione del sistema
    n = size(d);
    for i = 1:n
        d(i) = b(i)/d(i);
    end
end

function [b] = sistema_triang_sup(A, b)
% [b] = sistema_tirnag_sup(A, b)
% Calcola la soluzione di Ax=b con
% A: matrice triangolare superiore
% b: vettore colonna
% [b]: soluzione del sistema
    for j = length(A) : -1 : 1
        b(j)=b(j)/A(j,j);
        for i = 1 : j-1
            b(i) = b(i)-A(i,j)*b(j);
        end
    end
end

function [b] = sistema_triang_inf(A, b)
    for j = 1 : length(A)
        b(j) = b(j)/A(j,j);
        for i = j+1 : length(A)
            b(i) = b(i)-A(i,j)*b(j);
        end
    end
end
