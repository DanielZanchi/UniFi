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