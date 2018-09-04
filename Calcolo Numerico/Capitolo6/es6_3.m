tol = 10^(-5);

for n = 100:20:1000
    [x, i] = jacobi(matrice_sparsa(n), ones(n, 1), zeros(n,1), tol);
    
    plot(n, i, 'r.')
    hold on
end

function [x,i] = jacobi(A, b, x0, tol)
% function [x,i] = jacobi(A, b, x0, tol)
% Restituisce la soluzione del sistema lineare Ax=b approssimata con il
% metodo di Jacobi e il numero di iterazioni eseguite.
% A matrice utilizzata per il calcolo
% b vettore dei termini noti
% tol tolleranza dell' approssimazione
% x0 vettore di partenza
% x soluzione approssimata del sistema

D = diag(diag(A));
J = -inv(D) * (A-D);
q = D \ b;
x = J *x0 + q;
i = 1;
err = norm(x-x0) / norm(x);

while err > tol
    x0 = x;
    x = J * x0 + q;
    err = norm(x-x0) / norm(x);
    i = i + 1;
end
end
