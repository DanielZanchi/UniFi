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
