x = [1/2, 1/2]';
tolx = 10^-3;

F = @(x) [2*x(1) - x(2); 3*x(2)^2 - x(1)];
J = @(x) [2, -1; -1, 6*x(2)];

[x, i, in, er] = nonLineare_newtonMod(F, J, x, 500, tolx);

function [x, i, in, er] = nonLineare_newtonMod(F, J, x, imx, tolx)
    i = 0;
    xold = x+100;
    while (i < imx) && (norm(x-xold) > tolx)
        i = i+1;
        xold = x;
        [A, p] = fatt_LUpivot(feval(J,x));
        x = x+sistema_LUpivot(A, -feval(F,x), p);
    end
    er = norm(x-[1/12, 1/6]');
    in = norm(x-xold);
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


function [b]= sistema_LUpivot(A,b,p)
    % [b]= sistema_LUpivot(A,b,p)
    % Calcola la soluzione di Ax=b con A matrice LU con pivoting parziale
    % A: Matrice matrice LU con pivoting (generata da fatt_LUpivot)
    % b: vettore colonna
    % Output:
    % b: soluzione del sistema
    P = zeros(length(A));
    for i = 1:length(A)
        P(i, p(i)) = 1;
    end
    b = sistema_triang_inf(tril(A,-1)+eye(length(A)), P*b);
    b = sistema_triang_sup(triu(A), b);
end

function [b] = sistema_triang_inf(A, b)
    for j = 1 : length(A)
        b(j) = b(j)/A(j,j);
        for i = j+1 : length(A)
            b(i) = b(i)-A(i,j)*b(j);
        end
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