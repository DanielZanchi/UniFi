tol = 10^(-5);
n = 1000;

A = matrice_sparsa(1000);
b = ones(n, 1);

[x_gs, i_gs, err_gs] = gauss_seidel_err(A, b, zeros(n, 1), tol);
[x_j, i_j, err_j] = jacobi_err(A, b, zeros(n,1), tol);

semilogy(1:i_gs, err_gs)
hold on
semilogy(1:i_j, err_j)
hold off

function [x,i, err] = gauss_seidel_err(A, b, x0, tol)
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
    err(i) = norm(x-x0, inf) / norm(x);

    while err(i) > tol
        x0 = x;
        x = GS * x0 + b1;
        i = i + 1;
        err(i) = norm(x-x0, inf) / norm(x);
    end
end


function [x, i, err] = jacobi_err(A, b, x0, tol)

    D = diag(diag(A));
    J = - inv(D) * (A-D);

    q = D \ b;

    x = J*x0 + q;
    i = 1;
    err(i) = norm(x-x0)/norm(x);
    
    while err > tol
        x0 = x;
        x = J*x0 + q;
        i = i + 1;
        err(i) = norm(x-x0)/norm(x);
    end
end