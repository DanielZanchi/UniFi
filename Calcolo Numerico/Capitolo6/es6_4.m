tol = 10^(-5);

for n = 100:20:1000
    [x, i] = gauss_seidel(matrice_sparsa(n), ones(n, 1), zeros(n,1), tol);

    plot(n, i, 'r.')
    hold on
end

function [x,i] = gauss_seidel(A, b, x0, tol)
% function [x,i] = jacobi(A, b, tol, [xo, maxit])
% Restituisce la soluzione del sistema lineare Ax=b approssimata con il 
% metodo di Gauss-Seidel e il numero di iterazioni eseguite.
% A matrice utilizzata per il calcolo
% b vettore dei termini noti
% tol tolleranza dell' approssimazione
% x0 vettore di partenza
% x soluzione approssimata del sistema
    D = diag(diag(A));

    L = tril(A) - D;
    U = triu(A) - D;

    b1 = (D + L) \ b;

    DI = inv(D + L);
    GS = -DI * U;

    x = GS * x0 + b1;
    i = 1;
    err = norm(x-x0, inf) / norm(x);

    while err > tol
        x0 = x;
        x = GS * x0 + b1;
        err = norm(x-x0, inf) / norm(x);
        i = i + 1;
    end
end
